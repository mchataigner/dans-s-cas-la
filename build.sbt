scalaVersion := "2.10.4"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

traceLevel := -1

logLevel := Level.Info

// disable printing timing information, but still print [success]
showTiming := false

// disable printing a message indicating the success or failure of running a task
showSuccess := false

offline := true

parallelExecution in Test := false

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.10.4"

libraryDependencies += "org.scala-lang" % "scala-compiler" % "2.10.4"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4"

addCommandAlias("part1_1", "~ test-only part1_1_first_steps")

addCommandAlias("part1_2", "~ test-only part1_2_sequel")

addCommandAlias("part2", "~ test-only part2_we_need_to_go_deeper")

addCommandAlias("part3", "~ test-only part3_cons_and_nil")

addCommandAlias("part4", "~ test-only part4_type_classes")

addCommandAlias("part5", "~ test-only part5_the_return_of_the_bag")

addCommandAlias("part6", "~ test-only part6_bonus_event_sourcing")

addCommandAlias("go", "~ test-only HandsOnScala")
