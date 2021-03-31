package me.shy.spark.loganalyse

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
object ApacheAccessLog extends Serializable {
    def parseLog(log: String): ApacheAccessLog = {
        val columns = log.split("\\s+")
        val remoteIp = columns(0)
        val clientIdentified = columns(1)
        val userId = columns(2)
        val accessTime = s"${columns(3)}".replace("\"", "")
        val method = columns(5).replaceFirst("\"", "")
        val endPoint = columns(6)
        val protocol = columns(7).replaceAll("\"", "")
        val reponseCode = columns(8)
        val contentSize = columns(9).toLong
        ApacheAccessLog(remoteIp, clientIdentified, userId, accessTime, method, endPoint, protocol, reponseCode, contentSize)
    }
}

case class ApacheAccessLog(remoteIp: String,
                           clientIdentified: String,
                           userId: String,
                           accessDateTime: String,
                           method: String,
                           endPoint: String,
                           protocol: String,
                           responseCode: String,
                           contentSize: Long
                          )
