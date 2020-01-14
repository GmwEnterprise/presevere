export default function(context) {
  // 只在服务端执行
  console.log("server init...");
  console.log(context.type);
}
