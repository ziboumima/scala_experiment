package future

import akka.actor.SupervisorStrategy.Stop

import scala.annotation.tailrec
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import akka.actor.Actor
import akka.actor.Props
/**
  * Created by hibou on 13/02/16.
  */


//class Pong extends Actor {
//  def act() {
//    var pongCount = 0
//    while (true) {
//      receive {
//        case Stop =>
//          Console.println("Pong: stop")
//          exit()
//      }
//    }
//  }
//}


class Task1 extends Runnable {
  def run(): Unit = {
    while (true) {
      println("doing job 1")
      Thread.sleep(1000)
    }
  }
}


class Task2 extends Runnable {
  def run(): Unit = {
    while (true) {
      println("doing job 2")
      Thread.sleep(500)
    }


  }
}

object MyFuture {
  def task1(): Future[Unit] = Future {
    println("doing job 1")
    Thread.sleep(1000)
  }

  def task2(): Future[Unit] = Future {
    println("doing job 2")
    Thread.sleep(500)
  }

  def infiniteLoop(): Future[Unit] = {
    Future.sequence(List(task1(), task2())).flatMap(x => infiniteLoop())
  }

  def main(args: Array[String]): Unit = {
    println("Hello, world!")
    new Thread(new Task1).start()
    new Thread(new Task2).start()

  }


}
