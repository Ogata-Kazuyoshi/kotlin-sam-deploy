import './App.css'
import axios from "axios";


function App() {

    const apiGateway = import.meta.env.VITE_APIGATEWAY
    const handleClick = async () => {
        try {
            const res = await axios.get(`${apiGateway}/hello`)
            console.log("レスポンス:", res.data);
        } catch (err) {
            console.error("エラー:", err);
        }
    }

  return (
      <>
          <div>Kotlinでの確認</div>
          <div>
              <button onClick={handleClick}>
                  バックエンド通信
              </button>
          </div>
      </>
  )
}

export default App
