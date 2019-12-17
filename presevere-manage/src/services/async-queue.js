// /* eslint-disable no-unused-vars */
// const requestList = []

// function executeRequestList(index) {
//   if (index < requestList.length) {
//     requestList[index]().then(() => {
//       executeRequestList(index + 1)
//     }).catch(err => console.log(err))
//   }
// }

// requestList.push(() => new Promise((resolve, reject) => {
//   console.log(1)
//   setTimeout(() => {
//     console.log('first timeout.')
//     console.log(new Date().toString())
//     resolve()
//   }, 5000)
// }))

// requestList.push(() => new Promise((resolve, reject) => {
//   console.log(2)
//   setTimeout(() => {
//     console.log('second timeout.')
//     console.log(new Date().toString())
//     resolve()
//   }, 2000)
// }))

// executeRequestList(0)

// requestList.push(() => new Promise((resolve, reject) => {
//   console.log(3)
//   if (requestList.length > 3) {
//     requestList.splice(3, 1)
//     resolve()
//   }
// }))

// requestList.push(() => new Promise((resolve, reject) => {
//   console.log(4)
//   setTimeout(() => {
//     console.log('third timeout.')
//     console.log(new Date().toString())
//     resolve()
//   }, 10000)
// }))