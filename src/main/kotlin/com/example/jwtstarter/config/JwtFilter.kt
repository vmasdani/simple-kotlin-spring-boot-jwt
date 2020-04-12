package com.example.jwtstarter.config

import com.example.jwtstarter.dummydata.dummyUsers
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter(
    private val env: Environment
) : GenericFilterBean() {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val r = request as HttpServletRequest
        val w = response as HttpServletResponse

        println("Request uri: ${r.requestURI}")

        if (r.requestURI.startsWith("/auth") || // Continue request without filtering
            r.requestURI.startsWith("/generator")
        ) {
            chain?.doFilter(request, response)
        } else { // Check authorization using JWT
            val authHeader = r.getHeader("Authorization")

            if (authHeader != null) {
                // Split the token (From "Bearer <token_contents>", get only "<token_contents>")
                val bearerToken =
                    if (authHeader.split(" ").size > 1)
                        authHeader.split(" ").get(1)
                    else null

                // Decode SecretKey from application.properties
                val secretKeyString = env.getProperty("jwt.secret")
                val key =
                    if (secretKeyString != null)
                        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyString))
                    else null

                // Decode jwt, get username
                val subjectUsername =
                    if (key != null) {
                        try {
                            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(bearerToken).body.subject
                        }
                        catch(e: Throwable) {
                            println("Parsing jwt error: ${e}")
                            null
                        }
                    } else null

                println("Token: ${bearerToken}, Subject name: ${subjectUsername}")

                // Check for the existence of the found subject
                val userExists =
                    if (subjectUsername != null) {
                        val foundUser = dummyUsers.find { user -> user.username == subjectUsername }

                        if (foundUser != null) true else false
                    } else false

                // If user is found, Continue request. If not, return unauthorized.
                if (userExists)
                    chain?.doFilter(request, response)
                else {
                    w.status = HttpStatus.UNAUTHORIZED.value()
                    return
                }
            }
            else {
                w.status = HttpStatus.UNAUTHORIZED.value()
                return
            }
        }
    }
}