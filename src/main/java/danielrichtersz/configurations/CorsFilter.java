package danielrichtersz.configurations;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CorsFilter implements ContainerResponseFilter, ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add(
                "Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add(
                "Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add(
                "Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization");
        responseContext.getHeaders().add(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        responseContext.getHeaders().add("Access-Control-Expose-Headers", "Authorization");
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (isPreflightrequeest(requestContext)) {
            requestContext.abortWith(Response.ok().build());
            return;
        }
    }

    private static boolean isPreflightrequeest(ContainerRequestContext requestContext) {
        return requestContext.getHeaderString("Origin") != null
                && requestContext.getMethod().equalsIgnoreCase("OPTIONS");
    }
}
