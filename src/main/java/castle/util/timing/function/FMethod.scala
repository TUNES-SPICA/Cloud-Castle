package castle.util.timing.function

import castle.CC_CoreConfig

import java.util.UUID

/**
 * 方法函数<p>
 */
@FunctionalInterface trait FMethod {

  /**
   * 空方法
   */
  def method(id: String): Unit

  /**
   * 获取时间差值
   *
   * @return 方法执行时间，单位毫秒
   */
  final def difference: Long = {
    val timeStr = System.currentTimeMillis()
    val id = UUID.randomUUID().toString;
    method(id)
    val l = System.currentTimeMillis() - timeStr
    if (CC_CoreConfig.is_debug) println(s"case execution {id -> $id => $l}")
    l
  }

}