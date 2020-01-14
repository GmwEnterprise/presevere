export default function(context) {
  // 只在客户端执行
  console.log("client init...");
  console.log(context.type);
}
