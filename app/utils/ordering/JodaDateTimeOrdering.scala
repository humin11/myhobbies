package utils.ordering

import org.joda.time.DateTime

object JodaDateTimeOrdering {

  implicit def dateTimeOrdering: Ordering[DateTime] = Ordering.fromLessThan(_ isBefore _)

}
