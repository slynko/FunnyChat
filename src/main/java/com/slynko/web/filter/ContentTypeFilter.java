package com.slynko.web.filter;

import com.slynko.web.json.constants.Constant;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class ContentTypeFilter implements Filter {

  private String encoding;

  public void destroy() {
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
    request.setCharacterEncoding(encoding);
    response.setContentType("charset=" + encoding);
    response.setCharacterEncoding(encoding);
    chain.doFilter(request, response);
  }

  public void init(FilterConfig config) {
    encoding = ObjectUtils.defaultIfNull(config.getInitParameter("encoding"), Constant.ENCODING_DEFAULT);
  }
}
