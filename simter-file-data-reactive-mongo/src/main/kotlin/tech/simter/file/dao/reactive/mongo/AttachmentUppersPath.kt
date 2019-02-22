package tech.simter.file.dao.reactive.mongo

data class AttachmentUppersPath(private val id: String,
                                private val aggregate: List<Path>) {
  data class Path(val path: String)

  val fullPath: String
    get() {
      return aggregate.let { it.joinToString(separator = "/") { item -> item.path } }
    }
}