package itmo.labs.soa.exception;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AppExceptionHandler")
public class AppExceptionHandler extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Gson gson = new Gson();

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doDelete(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }
    protected void doPut(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request,
                              HttpServletResponse response) throws IOException {

        Throwable throwable = (Throwable) request
                .getAttribute("javax.servlet.error.exception");

        if (throwable instanceof InvalidIDException ||
                throwable instanceof NegativeValueException ||
                throwable instanceof ParamNotFound ||
                throwable instanceof ValueBeyondLimit)
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        else if (throwable instanceof NoSuchWorkerException)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        sendAsJson(response, throwable);

    }


    private void sendAsJson(HttpServletResponse response, Object obj) throws IOException {

        response.setContentType("application/json");
        String res = gson.toJson(obj);
        PrintWriter out = response.getWriter();

        out.print(res);
        out.flush();
    }
}
