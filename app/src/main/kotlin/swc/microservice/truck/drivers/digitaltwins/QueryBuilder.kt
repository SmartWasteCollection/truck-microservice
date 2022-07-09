package swc.microservice.truck.drivers.digitaltwins

import swc.microservice.truck.drivers.digitaltwins.QueryBuilder.QueryElements.ALL
import swc.microservice.truck.drivers.digitaltwins.QueryBuilder.QueryElements.FROM_DIGITAL_TWINS
import swc.microservice.truck.drivers.digitaltwins.QueryBuilder.QueryElements.SELECT
import swc.microservice.truck.drivers.digitaltwins.QueryBuilder.QueryElements.WHERE

class QueryBuilder(val string: String = "") {

    object QueryElements {
        const val SELECT = "SELECT"
        const val ALL = "*"
        const val FROM_DIGITAL_TWINS = "FROM digitaltwins"
        const val WHERE = "WHERE"
        const val EQUALS = "="
    }

    fun selectAll(): QueryBuilder = QueryBuilder("$SELECT $ALL $FROM_DIGITAL_TWINS")

    fun where(element: String, operation: String, value: String): QueryBuilder = QueryBuilder("$string $WHERE $element $operation $value")

    fun build(): String = string
}
