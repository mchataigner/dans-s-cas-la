package support

import org.scalatest._
import org.scalatest.matchers.{Matcher, ShouldMatchers}
import org.scalatest.events._
import org.scalatest.exceptions.{TestPendingException}

import recorder._

import language.experimental.macros
import scala.Some


trait HandsOnSuite extends MyFunSuite with Matchers {
  def __ : Matcher[Any] = {
    throw new NotImplementedError("__")
  }

  implicit val suite:MyFunSuite = this



  def anchor[A](a:A):Unit = macro RecorderMacro.anchor[A]

  def exercice(testName:String)(testFun: Unit)(implicit suite: MyFunSuite, anchorRecorder: AnchorRecorder):Unit = macro RecorderMacro.apply

  /*override protected def test(testName: String, tags: Tag*)(testFun: => Unit):Unit


  = macro RecorderMacro.apply  */

  private class ReportToTheStopper(other: Reporter, stopper: Stopper) extends Reporter {
    var failed = false

    def headerFail =    "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n               TEST FAILED                 \n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
    def footerFail =    "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
    def headerPending = "*******************************************\n               TEST PENDING                \n*******************************************"
    def footerPending = "*******************************************"

    val logInfo = info
    def sendInfo(header: String, suite: String, test: String, location: Option[String], message: Option[String], context: Option[String], footer: String) {
      header.split("\n").foreach(logInfo(_))

      location.collect({ case f =>
        logInfo( "File  : " + f.replace("\n","") )
      })

      logInfo( "Suite : " + suite.replace("\n","") )

      logInfo( "Test  : " + test.replace("\n","") )

      message.collect({ case m =>
        logInfo("")
        m.split("\n").foreach( logInfo(_) )
      })
      context.collect({ case c =>
        logInfo("")
        c.split("\n").foreach( logInfo(_) )
      })
      logInfo("")
      footer.split("\n").foreach(logInfo(_))
      stopper.requestStop()

    }

    def sendFail(e:MyException, suite:String, test:String) = {
      sendInfo(headerFail
          , suite
          , test
          , e.fileNameAndLineNumber
          , Option(e.getMessage)
          , e.context
          , footerFail
        )
    }

    def sendPending(e:MyException, suite:String, test:String, mess:Option[String]) = {
      sendInfo(headerPending
          , suite
          , test
          , e.fileNameAndLineNumber
          , mess
          , e.context
          , footerPending
        )
    }

    def apply(event: Event) {
      event match {
        case e: TestFailed => {

          e.throwable match {
      //pour les erreurs d'assertions => sans stacktrace
            case Some(failure: MyTestFailedException) =>
              sendFail(failure, e.suiteName, e.testName)
      //pour les __ => avec context
            case Some(pending: MyTestPendingException) =>
              sendPending(pending, e.suiteName, e.testName, Some("You should replace __ by correct values"))
      //pour les ??? => sans context
            case Some(pending: MyNotImplException) =>
              sendPending(pending, e.suiteName, e.testName, Some("You should replace ??? by correct implementation"))
      //pour les autres erreurs => avec stacktrace
            case Some(failure: MyException) =>
              sendFail(failure, e.suiteName, e.testName)
      //ça ne devrait pas arriver
            case Some(e) =>
              println(e)
              println(e.getStackTrace.take(10).mkString("\n"))
              println("something went wrong")
      //ça non plus, un TestFailed a normalement une excepetion attachée
            case None =>
              sendInfo(headerFail
                , e.suiteName
                , e.testName
                , None
                , None
                , None
                ,
                footerFail
              )
          }
        }
        case e: TestPending =>
          sendInfo(headerPending
            , e.suiteName
            , e.testName
            , None
            , Some("pending")
            , None
            , footerPending)
        case _ =>
          other(event)
      }
    }
  }

  protected override def runTest(testName: String, args: Args): Status = {
//                                 reporter: Reporter, stopper: Stopper, configMap: Map[String, Any], tracker: Tracker) {
//    if (!args.stopper.stopRequested) {
    val _args = args.copy(reporter = new ReportToTheStopper(args.reporter, args.stopper))
    if(_args.stopper.stopRequested){
      FailedStatus
    } else {
      super.runTest(testName, _args)
    }
//    }
  }
}

//object HandsOnSuite {
//  object partie1 extends Tag("support.partie1")
//  object partie2 extends Tag("support.partie2")
//}
