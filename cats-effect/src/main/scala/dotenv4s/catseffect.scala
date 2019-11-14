package dotenv4s

import java.nio.file.Path

import cats.effect.Sync

object catseffect {
  def loadF[F[_]]()(implicit F: Sync[F]): F[Unit] =
    F.delay(load())
  def loadF[F[_]](filename: String)(implicit F: Sync[F]): F[Unit] =
    F.delay(load(filename))
  def loadF[F[_]](path: Path)(implicit F: Sync[F]): F[Unit] =
    F.delay(load(path))
  def loadF[F[_]](path: Path, filename: String)(implicit F: Sync[F]): F[Unit] =
    F.delay(load(path, filename))
}
