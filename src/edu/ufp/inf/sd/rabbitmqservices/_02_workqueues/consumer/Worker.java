/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.inf.sd.rabbitmqservices._02_workqueues.consumer;

import com.rabbitmq.client.*;
import edu.ufp.inf.sd.rabbitmqservices._02_workqueues.producer.NewTask;
import edu.ufp.inf.sd.rabbitmqservices.util.RabbitUtils;


/**
 * Round-robin dispatching:
 *  One of the advantages of using a Task Queue is the ability to easily
 *  parallelise work. If we are building up a backlog of work, we can just add
 *  workers and scale easily.
 * 
 *  Run 2 worker instances at the same time (one on each shell).
 *  They will both get messages from the queue, since, by default, RabbitMQ will
 *  send each message to the next client (in sequence).
 *  On average, every client will get the same number of messages. This way of
 *  distributing messages is called round-robin (try this out with 3+ workers).
 * 
 * 
 * Message acknowledgment:
 *  With current channel queue, once RabbitMQ delivers a message to the customer it
 *  immediately marks it for deletion. In this case, if you kill a worker we
 *  will lose the message it was just processing. We also lose all the messages
 *  that were dispatched to this particular worker but were not yet handled.
 *
 *  To not lose any tasks (in case a worker dies) and deliver them to another worker,
 *  RabbitMQ supports message acknowledgments, i.e., an ack is sent back by the client
 *  to tell RabbitMQ that a particular message has been received, processed and
 *  that RabbitMQ is free to delete it.
 *
 *  If a client dies (i.e., channel is closed, connection is closed, or
 *  TCP connection is lost) without sending an ack, RabbitMQ will understand
 *  that a message was not processed fully and will re-queue it.
 *  If there are other consumers online at the same time, it will then quickly
 *  re-deliver it to another client. That way you can be sure that no message
 *  is lost, even if the workers occasionally die.
 *  There are no message timeouts and RabbitMQ will re-deliver the message when
 *  the client dies. It is fine even if processing a message takes a long time.
 *  "Manual message acknowledgments" are turned on by default (we may explicitly
 *  turned them off via the autoAck=true flag).
 * 
 * Forgotten acknowledgment:
 *  To debug lack of ack use rabbitmqctl to print the messages_unacknowledged field:
 *    - Linux/Mac:
 *     sudo rabbitmqctl list_queues name messages_ready messages_unacknowledged
 *    - Win:
 *     rabbitmqctl.bat list_queues name messages_ready messages_unacknowledged
 * 
 * Message durability:
 *  Messages/Tasks will be lost if RabbitMQ server stops, because when RabbitMQ
 *  quits or crashes it will forget the queues and messages unless you tell it not to.
 *
 *  Two things are required to make sure that messages are not lost, i.e., mark both
 *  the queue and messages as durable:
 *      1) declare the queue as *durable* (so RabbitMQ will never lose the queue);
 *      2) mark messages as persistent by setting MessageProperties.PERSISTENT_TEXT_PLAIN.
 *
 *  NB: persistence guarantees ARE NOT strong, i.e., may be cached and
 *  not immediately saved/persisted.
 * 
 * Fair dispatch:
 *  RabbitMQ dispatches a message when the message enters the queue. It does not
 *  look at the number of unacknowledged messages for a client. It just blindly
 *  dispatches every n-th message to the n-th client. Hence, a worker could get
 *  all heavy tasks while another the light ones.
 *
 *  To guarantee fairness use basicQos() method for setting prefetchCount = 1.
 *  This tells RabbitMQ not to give more than one message to a worker at a time,
 *  i.e. do not dispatch new message to a worker until it has not processed and
 *  acknowledged the previous one. Instead, dispatch it to the next worker
 *  that is not still busy.
 *
 * Challenge:
 *  1. Create a LogWorker for appending the message to a log file;
 *  2. Create a MailWorker for sending an email (use javamail API
 *  <https://javaee.github.io/javamail/>)
 * 
 * @author rui
 */
public class Worker {

    public static void main(String[] argv) throws Exception {
        try {
            RabbitUtils.printArgs(argv);

            //Read args passed via shell command
            String host=argv[0];
            int port=Integer.parseInt(argv[1]);
            String queueName=argv[2];

            /* Open a connection and a channel, and declare the queue from which to consume.
            Declare the queue here, as well, because we might start the client before the publisher. */
            Connection connection=RabbitUtils.newConnection2Server(host, port, "guest", "guest");
            Channel channel=RabbitUtils.createChannel2Server(connection);

            /* Declare a queue as Durable (queue won't be lost even if RabbitMQ restarts);
            NB: RabbitMQ doesn't allow to redefine an existing queue with different
            parameters, need to create a new one */
            boolean durable = true;
            //channel.queueDeclare(Send.QUEUE_NAME, false, false, false, null);
            channel.queueDeclare(queueName, durable, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            /* The server pushes messages asynchronously, hence we provide a DefaultConsumer callback
            that will buffer the messages until ready to use them. */
            //Set QoS: accept only one unacked message at a time; and force dispatch to next worker that is not busy.
            int prefetchCount = 1;
            channel.basicQos(prefetchCount);

            //Create consumer which will doWork()
            /*
            final Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");

                    System.out.println(" [x] Received '" + message + "'");
                    try {
                        doWork(message);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        System.out.println(" [x] Done");
                        //The Worker MUST Manually ACK each finalised task.
                        //This code makes sure that even if worker is killed (CTRL+C) while processing a message,
                        //nothing will be lost.
                        //Soon after the worker dies all unacknowledged messages will be redelivered.
                        //Ack must be sent on the same channel message was received on, otherwise raises exception
                        //  (channel-level protocol exception).
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                }
            };
            //Set this flag=false for worker to send a proper acknowledgment (once it is done with a task).
            //boolean autoAck = true; //When true disables "Manual message acknowledgments"
            boolean autoAck = false; //"Manual message acknowledgments" enabled
            channel.basicConsume(NewTask.TASK_QUEUE_NAME, autoAck, consumer);
            */
            DeliverCallback deliverCallback=(consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                try {
                    doWork(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [x] Done processing task");
                    //Worker must Manually ack each finalised task, hence, even if worker is killed
                    //(CTRL+C) while processing a message, nothing will be lost.
                    //Soon after the worker dies all unacknowledged messages will be redelivered.
                    //Ack must be sent on the same channel message it was received,
                    // otherwise raises exception (channel-level protocol exception).
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            };
            //boolean autoAck = true; //When true disables "Manual message acknowledgments"
            //Set flag=false for worker to send proper ack (once it is done with a task).
            boolean autoAck = false;
            //Register handler deliverCallback()
            channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> { });

        } catch (Exception e) {
            //Logger.getLogger(Recv.class.getName()).log(Level.INFO, e.toString());
            e.printStackTrace();
        }

    }

    /** Fake a second of work for every dot in the message body */
    private static void doWork(String task) throws InterruptedException {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
