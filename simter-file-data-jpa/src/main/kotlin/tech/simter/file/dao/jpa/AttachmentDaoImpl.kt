package tech.simter.file.dao.jpa

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler
import tech.simter.file.dao.AttachmentDao
import tech.simter.file.po.Attachment
import java.util.concurrent.Callable


/**
 * The JPA implementation of [AttachmentDao].
 *
 * @author RJ
 */
@Component
class AttachmentDaoImpl @Autowired constructor(
  val jpaDao: AttachmentJpaDao,
  @Qualifier("jpaScheduler") val scheduler: Scheduler
) : AttachmentDao {
  private fun <T> async(callable: Callable<T>): Mono<T> {
    return Mono.fromCallable(callable).publishOn(scheduler)
  }

  override fun save(entity: Attachment): Mono<Attachment> {
    return async(Callable(function = { jpaDao.save(entity) }))
  }
}