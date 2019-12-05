package cn.gmwenterprise.presevere.common;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

public final class MarkdownUtils {

    private static Parser parser;
    private static HtmlRenderer renderer;

    static {
        MutableDataSet options = new MutableDataSet();
        parser = Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options).build();
    }

    public static String render(String markdown) {
        // You can re-use parser and renderer instances
        Node document = parser.parse(markdown);
        return renderer.render(document);
    }

    public static void main(String[] args) {
        System.out.println(render("---\n" +
            "title: 关于token在刚刚过期后的刷新问题\n" +
            "date: 2019-11-30 22:29:23\n" +
            "tags:\n" +
            "  - javascript\n" +
            "\n" +
            "---\n" +
            "\n" +
            "### Token无缝刷新\n" +
            "\n" +
            "前后端分离的系统，假如这个系统是给客服人员使用，客服人员可能要在长达八个小时的时间上不停地工作；为了安全考虑，不给token设定永久的时常，而是给它一个可刷新时间区域：*在当前token刚刚过期后的一段时间发起的请求，系统可以无缝刷新一次token，从而为使用者带来更好的体验*，这是非常重要的。\n" +
            "\n" +
            "#### Axios中的实现\n" +
            "\n" +
            "##### 原理简单阐述\n" +
            "\n" +
            "实现很简单，在拦截器当中检测返回数据，检测到刚刚过期可以刷新的情况，则在拦截器中直接发起刷新token的请求，获得到新token后保存，再执行一次失败的请求，执行完毕返回。\n" +
            "\n" +
            "##### 会遇到的问题\n" +
            "\n" +
            "axios是异步执行的，假如在刷新token的过程中又有其他携带旧token并收到需要刷新的标识后，又如何处理呢？\n" +
            "\n" +
            "设置一个全局标识，表明当前是否正在刷新token，如果正在刷新，就拒绝。这样显然是不好的。在一次需求中可能要发起多个请求这是很正常的事情。为了不让其他请求失败，我的做法是，创建一个异步任务顺序执行器，在刷新token期间还携带旧token并返回过期的请求，则重新将本请求加入到这个执行器的末尾；而执行器则是在首次检测到过期时进行初始化，并开始执行第一个任务，也就是刷新token任务。\n" +
            "\n" +
            "##### 代码\n" +
            "\n" +
            "放代码之前我先给出一些解释：我并没有使用常规的http状态码来判断响应结果，而是在后端程序中讲所有非系统错误的大部分异常全部封装为了自定义的错误码并返回给前台，我在拦截器中自己来判断返回结果。\n" +
            "\n" +
            "``` javascript\n" +
            "/* eslint-disable no-unused-vars */\n" +
            "'use strict'\n" +
            "\n" +
            "import Vue from 'vue'\n" +
            "import axios from 'axios'\n" +
            "import router from '../router.js'\n" +
            "import { Notification, Message } from 'element-ui'\n" +
            "\n" +
            "// Full config:  https://github.com/axios/axios#request-config\n" +
            "// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || ''\n" +
            "// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN\n" +
            "// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'\n" +
            "\n" +
            "let config = {\n" +
            "  // baseURL: process.env.baseURL || process.env.apiUrl || ''\n" +
            "  // timeout: 60 * 1000, // Timeout\n" +
            "  // withCredentials: true, // Check cross-site Access-Control,\n" +
            "}\n" +
            "\n" +
            "const _axios = axios.create(config)\n" +
            "\n" +
            "_axios.interceptors.request.use(\n" +
            "  function (config) {\n" +
            "    // Do something before request is sent\n" +
            "    if (!config.url.includes('http')) {\n" +
            "      config.url = 'http://127.0.0.1:4399' + config.url\n" +
            "    }\n" +
            "    const token = localStorage.getItem('token')\n" +
            "    if (token) {\n" +
            "      config.headers.Authorization = token\n" +
            "    }\n" +
            "    return config\n" +
            "  },\n" +
            "  function (error) {\n" +
            "    // Do something with request error\n" +
            "    return Promise.reject(error)\n" +
            "  }\n" +
            ")\n" +
            "\n" +
            "let refreshTokenFlag = false\n" +
            "let requestList = []\n" +
            "\n" +
            "function executeRequests(index) {\n" +
            "  if (index < requestList.length) {\n" +
            "    requestList[index]().then(() => {\n" +
            "      executeRequests(index + 1)\n" +
            "    })\n" +
            "  } else {\n" +
            "    refreshTokenFlag = false\n" +
            "    requestList.splice(0, requestList.length)\n" +
            "  }\n" +
            "}\n" +
            "\n" +
            "// Add a response interceptor\n" +
            "_axios.interceptors.response.use(\n" +
            "  function (response) {\n" +
            "    console.log(` >>> 拦截器开始`)\n" +
            "    console.log(response.config.url)\n" +
            "    console.log(response.config.headers.Authorization)\n" +
            "    console.log(response.data)\n" +
            "    // Do something with response data\n" +
            "    /** \n" +
            "     * 在这里返回Promise.reject会进入代码的catch块;\n" +
            "     * 返回Promise.resolve或正常数据会进入then块;\n" +
            "     * 不返回 = 返回undefined也会进入then块\n" +
            "     */\n" +
            "    // 成功\n" +
            "    if (response.data.code === 1) {\n" +
            "      return response.data\n" +
            "    }\n" +
            "    if (response.data.code === 2 || response.data.code === 4) {\n" +
            "      // 失败 || 无权访问\n" +
            "      Message({\n" +
            "        message: response.data.data || '网络繁忙！',\n" +
            "        type: 'error',\n" +
            "        center: true\n" +
            "      })\n" +
            "      return Promise.reject(response.data)\n" +
            "    } else if (response.data.code === 3) {\n" +
            "      // 需要登录验证权限\n" +
            "      const redirectUrl = router.currentRoute.fullPath\n" +
            "      router.push({\n" +
            "        path: '/login',\n" +
            "        query: {\n" +
            "          redirectUrl\n" +
            "        }\n" +
            "      })\n" +
            "      return Promise.reject(response.data)\n" +
            "    } else if (response.data.code === 5) {\n" +
            "      // 需要刷新token\n" +
            "      const userId = response.data.data\n" +
            "      if (refreshTokenFlag) {\n" +
            "        // 正在刷新\n" +
            "        return new Promise((resolve, reject) => {\n" +
            "          requestList.push(async function () {\n" +
            "            try {\n" +
            "              const r = await _axios(response.config)\n" +
            "              resolve(r)\n" +
            "            } catch (err) {\n" +
            "              reject(err)\n" +
            "            }\n" +
            "          })\n" +
            "        })\n" +
            "      } else {\n" +
            "        // 未刷新\n" +
            "        refreshTokenFlag = true\n" +
            "        return new Promise((resolve, reject) => {\n" +
            "          requestList.push(async function () {\n" +
            "            try {\n" +
            "              const result = await _axios.post(`/sign/refreshToken/${userId}`)\n" +
            "              localStorage.setItem('token', result.data)\n" +
            "              const r = await _axios(response.config)\n" +
            "              resolve(r)\n" +
            "            } catch (err) {\n" +
            "              reject(err)\n" +
            "            }\n" +
            "          })\n" +
            "          executeRequests(0)\n" +
            "        })\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  function (error) {\n" +
            "    console.log('Ajax系统错误')\n" +
            "    Notification.error({\n" +
            "      title: '系统错误',\n" +
            "      message: `${error || 'No message available'}`\n" +
            "    })\n" +
            "    return Promise.reject(error)\n" +
            "  }\n" +
            ")\n" +
            "\n" +
            "Plugin.install = function (Vue, options) {\n" +
            "  Vue.axios = _axios\n" +
            "  window.axios = _axios\n" +
            "  Object.defineProperties(Vue.prototype, {\n" +
            "    axios: {\n" +
            "      get() {\n" +
            "        return _axios\n" +
            "      }\n" +
            "    },\n" +
            "    $axios: {\n" +
            "      get() {\n" +
            "        return _axios\n" +
            "      }\n" +
            "    },\n" +
            "  })\n" +
            "}\n" +
            "\n" +
            "Vue.use(Plugin)\n" +
            "\n" +
            "export default Plugin\n" +
            "\n" +
            "```\n" +
            "\n" +
            "##### 其他\n" +
            "\n" +
            "其实无痛刷新token的方法有很多，我在之前用到的方法则是在springmvc的Interceptor以及ResponseBodyAdvice中进行token的刷新，在返回结果中携带一个token刷新标志以及刷新后的token；这带来了一个问题：所有的token刷新都会在后端来解决，对于一个小系统，小网站来说，很可能会影响程序执行效率（当然大系统应该有自己的一套方案，这我就不知道了，若是有大佬路过也很期待您能在评论中给出一个小建议，感激不尽）。\n" +
            "\n" +
            "在实现通过前端来刷新的这个方法后，经过测试发现，体验还不错，在刷新的过程会有比较低的延迟但完全可以接受（延迟若是很大的话可能本身列入队列的请求也很复杂），所以写下这篇文章以便记录。\n" +
            "\n"));
    }
}
