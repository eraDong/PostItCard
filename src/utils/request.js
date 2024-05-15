import axios from 'axios'
const baseURL = 'http://127.0.0.1:8080'

const instance = axios.create({
  // TODO 1. 基础地址，超时时间
  baseURL,
  timeout: 10000
})

instance.interceptors.request.use(
  (config) => {
    // TODO 2. 携带token
    return config
  },
  (err) => {
    // TODO 5. 处理401错误

    return Promise.reject(err)
  }
)

instance.interceptors.response.use(
  (res) => {
    // TODO 3. 处理业务失败

    // TODO 4. 摘取核心响应数据
    return res
  },
  (err) => {
    // TODO 5. 处理401错误

    return Promise.reject(err)
  }
)

export default instance
export { baseURL }
