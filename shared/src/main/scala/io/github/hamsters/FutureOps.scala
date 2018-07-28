package io.github.hamsters

import scala.concurrent.{ExecutionContext, Future}
import scala.util._

object FutureOps {

  /**
    * Converts an Either[Throwable, A] to a Future[A] that may raise a Throwable
    *
    * @param either
    * @tparam A
    * @return a Future[A]
    */
  def fromEither[A](either: Either[Throwable, A]): Future[A] = {
    either match {
      case Right(a) => Future.successful(a)
      case Left(error) => Future.failed(error)
    }
  }

  implicit class FutureEither[A](futureEitherStack: Future[Either[Throwable, A]]) {
    def squash(implicit ec: ExecutionContext): Future[A] = futureEitherStack.flatMap(fromEither)
  }

  /**
    * Converts a Try[A] to a Future[A] that may raise a Throwable
    *
    * @param tr
    * @tparam A
    * @return a Future[A]
    */
  def fromTry[A](tr: Try[A]): Future[A] = tr match {
    case Success(a) => Future.successful(a)
    case Failure(th) => Future.failed(th)
  }

  implicit class FutureTry[A](futureTryStack: Future[Try[A]]) {
    def squash(implicit ec: ExecutionContext): Future[A] = futureTryStack.flatMap(fromTry)
  }

  /**
    * Converts an Option[A] to a Future[A] that may raise an empty value error
    *
    * @param option
    * @tparam A
    * @return a Future[A]
    */
  def fromOption[A](option: Option[A]): Future[A] = option match {
    case Some(a) => Future.successful(a)
    case None => Future.failed(new EmptyValueError)
  }

  implicit class FutureOption[A](futureOptionStack: Future[Option[A]]) {
    def squash(implicit ec: ExecutionContext): Future[A] = futureOptionStack.flatMap(fromOption)
  }

}

