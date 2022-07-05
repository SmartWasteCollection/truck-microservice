package swc.microservice.truck.drivers

import swc.microservice.truck.drivers.QueryBuilder.QueryElements.SELECT
import swc.microservice.truck.drivers.QueryBuilder.QueryElements.ALL
import swc.microservice.truck.drivers.QueryBuilder.QueryElements.COUNT
import swc.microservice.truck.drivers.QueryBuilder.QueryElements.FROM_DIGITAL_TWINS
import swc.microservice.truck.drivers.QueryBuilder.QueryElements.WHERE

class QueryBuilder(val string: String = "") {

    object QueryElements {
        const val SELECT = "SELECT"
        const val ALL = "*"
        const val COUNT = "COUNT()"
        const val FROM_DIGITAL_TWINS = "FROM digitaltwins"
        const val WHERE = "WHERE"
        const val EQUALS = "="
    }

    fun selectAll(): QueryBuilder = QueryBuilder("$string $SELECT $ALL $FROM_DIGITAL_TWINS")

    fun selectCount(): QueryBuilder = QueryBuilder("$string $SELECT $COUNT $FROM_DIGITAL_TWINS")

    fun where(element: String, operation: String, value: String): QueryBuilder = QueryBuilder("$string $WHERE $element $operation $value")

    fun build(): String = string
}