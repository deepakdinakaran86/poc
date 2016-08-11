#!/usr/bin/expect  -f
#! /bin/bash

package require Expect


puts "\nPlease choose the Option "
puts "\n------------------------\n "
puts "\n 1.Create Device Cloud DB"
puts "\n 2.Delete Device Cloud DB"
puts "\n 3.Exit"

gets stdin x

set node "10.234.31.231\r"



switch $x {

	1 {

		spawn  /home/pcs/Cassandra-2.1/apache-cassandra-2.1.1/bin/cqlsh $node
			expect ">"
			send "CREATE KEYSPACE IF NOT EXISTS DEVICECLOUD WITH replication = {'class': 'SimpleStrategy','replication_factor': 2}; \r"
			expect ">"


			set fd [open "/home/pcs/Cassandra-2.1/apache-cassandra-2.1.1/bin/tablename"  "r"]

			send "USE DEVICECLOUD; \r"
			while {[gets $fd line] >= 0} {
				send "CREATE TABLE $line ( device bigint, date text, time timestamp, data int, tagname text, ext blob, PRIMARY KEY ((device,date),time) ); \r"
					expect ">"
			}

		close $fd

	}

	2 {
		puts "\n Are you sure want to delete Device Cloud DB \n"
			puts " Enter (Y/N) : "
			gets stdin y

			switch $y {
				y {

					spawn  /home/pcs/Cassandra-2.1/apache-cassandra-2.1.1/bin/cqlsh $node
						expect ">"
						send "CREATE KEYSPACE IF NOT EXISTS DEVICECLOUD WITH replication = {'class': 'SimpleStrategy','replication_factor': 2}; \r"
						expect ">"

						send "DROP KEYSPACE DEVICECLOUD; \r "

				}
				n {
					puts "\n Getting out.... (0-0) "
				}


			}




	}
	3 {
		puts "three"

	}

}



puts $x
