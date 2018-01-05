//package free
//import scala.concurrent.ExecutionContext.Implicits.global
//import scala.concurrent.duration.Duration
//import scala.concurrent.{Await, Future}
//import scalaz.Scalaz._
//import scalaz.{Free, ~>, _}
//
//object FreeExample {
//  type UserId = Int
//  type UserName = String
//  type UserPhoto = String
////  implicit val monadFuture = MonadFuture
//  final case class Tweet(userId: UserId, msg: String)
//  final case class User(id: UserId, name: UserName, photo: UserPhoto)
//
//  // Services represent web services we can call to fetch data
//  sealed trait Service[A]
//  final case class GetTweets(userId: UserId) extends Service[List[Tweet]]
//  final case class GetUserName(userId: UserId) extends Service[UserName]
//  final case class GetUserPhoto(userId: UserId) extends Service[UserPhoto]
//
//  // A request represents a request for data
//  final case class Request[A](service: Service[A])
//
//  type CoyonedaResultSetOp[A] = Coyoneda[Request, A]
//  type ResultSetIO[A] = Free[CoyonedaResultSetOp, A]
//
//
////  def sequence[A, B](input: List[Free[A, B]]) = {
////    input.foldLeft(Free.)
////  }
//
//  def fetch[A](service: Service[A]): Free[Request, A] =
//    Free.liftF[Request, A](Request(service) : Request[A])
//
//  object ToyInterpreter extends (Request ~> Future) {
//
//    def apply[A](in: Request[A]): Future[A] = in match {
//      case Request(service) =>
//        service match {
//          case GetTweets(userId) =>
//            println(s"Getting tweets for user $userId")
//            Future.successful(List(Tweet(1, "Hi"), Tweet(2, "Hi"), Tweet(1, "Bye")))
//
//          case GetUserName(userId) =>
//            println(s"Getting user name for user $userId")
//            val r = userId match {
//              case 1 => "Agnes"
//              case 2 => "Brian"
//              case _ => "Anonymous"
//            }
//            Future.successful(r)
//
//          case GetUserPhoto(userId) =>
//            println(s"Getting user photo for user $userId")
//            val r = userId match {
//              case 1 => ":-)"
//              case 2 => ":-D"
//              case _ => ":-|"
//            }
//            Future.successful(r)
//        }
//    }
//  }
//
//  object Example {
//
//    val theId: UserId = 1
//
//    def getUser(id: UserId): Free[Request, User] =
//      for {
//        name  <- fetch(GetUserName(id))
//        photo <- fetch(GetUserPhoto(id))
//      } yield User(id, name, photo)
//
//    val free: Free[Request, List[(String, User)]] =
//    for {
//      tweets <- fetch(GetTweets(theId))
//      result <- List(Tweet(1, "Hi"), Tweet(2, "Hi"), Tweet(1, "Bye")) map { tweet: Tweet =>
//        for {
//          user <- getUser(tweet.userId)
//        } yield (tweet.msg -> user)
//      } sequenceU
//    } yield result
//
//    def run =
//      free.foldMap(ToyInterpreter)
//  }
//
//  def main(args: Array[String]): Unit = {
//    val toto = Example.run
//    Await.result(toto, Duration.Inf)
//  }
//
//}
