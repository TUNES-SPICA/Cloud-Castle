package castle.util.timing

import castle.util.timing.function.FMethod

/**
 * 时间工具类
 *
 * @author ran
 * @version 2
 */
object UtilTime {

  /**
   * 获取方法执行时间
   *
   * @param f 执行方法
   * @return 方法执行时间，单位毫秒
   */
  def getExecutionTime(f: FMethod): Long = f.difference

}