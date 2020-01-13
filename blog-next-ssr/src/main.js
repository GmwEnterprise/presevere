import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

Vue.config.productionTip = false;

export default function createApp() {
  console.log("ssr entry ...");
  // create a root component
  const app = {
    router,
    store,
    head: {},
    render: h => h(App)
  };

  // return the root component
  return app;
}
