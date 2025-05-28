// package com.universidad.performance
//
// import io.gatling.core.Predef._
// import io.gatling.http.Predef._
// import org.junit.jupiter.api.Disabled
//
// @Disabled("Skipping due to build issues.")
// class EstudianteApiSimulation extends Simulation {
//   val httpProtocol = http.baseUrl("http://localhost:8080") // Cambia el puerto si usas otro
//
//   val scn = scenario("CRUD Estudiante Performance")
//     .exec(
//       http("Crear Estudiante")
//         .post("/api/estudiantes")
//         .header("Content-Type", "application/json")
//         .body(StringBody("""{"nombre":"TestPerf","apellido":"User","dni":"99999999","email":"perfuser@test.com"}""")).asJson
//         .check(status.is(201))
//     )
//     .pause(1)
//     .exec(
//       http("Listar Estudiantes")
//         .get("/api/estudiantes")
//         .check(status.is(200))
//     )
//
//   setUp(
//     scn.inject(atOnceUsers(10)) // 10 usuarios simultáneos
//   ).protocols(httpProtocol)
// }
