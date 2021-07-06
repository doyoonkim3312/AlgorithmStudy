package tasks

import contributors.User

/*
TODO: Write aggregation code.

 In the initial list each user is present several times, once for each
 repository he or she contributed to.
 Merge duplications: each user should be present only once in the resulting list
 with the total value of contributions for all the repositories.
 Users should be sorted in a descending order by their contributions.

 The corresponding test can be found in test/tasks/AggregationKtTest.kt.
 You can use 'Navigate | Test' menu action (note the shortcut) to navigate to the test.
*/
fun List<User>.aggregate(): List<User> {
    val map = this.groupBy { it.login }.toMutableMap()
    val temp = mutableListOf<User>()
    for (key: String in map.keys) {
        temp.add(User(key, map[key]!!.sumBy { it.contributions }))
    }
    temp.sortByDescending { it.contributions }
    return temp
}

// Solution
fun List<User>.aggregate(text: String): List<User> {
    // this is solution. The argument value 'text' is just placeholder.
    return groupBy { it.login }.map { (login, group) -> User(login, group.sumBy { it.contributions }) }
        .sortedByDescending { it.contributions }
}