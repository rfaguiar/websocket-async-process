import React, {useEffect, useState} from 'react';
import './App.css';
import axios from "axios";
import connect from "./Websocket";

function App() {
  const [username, setUsername] = useState('');
  const [tableData, setTableData] = useState<string[]>([]);

  useEffect(() => {
    console.log('tableData', tableData);
  }, [tableData]);

  async function getToken() {
    let axiosResponse = await axios.post('/api/token', {username: username});
    const solicitationId = axiosResponse.data.solicitationId
    let message = "Received solicitationId: " + solicitationId;
    addMessageTableData(message);
    connect({username: username, id: solicitationId}, addMessageTableData);
  }

  function addMessageTableData(message: string) {
    setTableData(tableData => [...tableData, message]);
  }


  return (
      <div id="main-content" className="container">
        <div className="row">
          <div className="col-md-12">
            <form className="form-inline" onSubmit={event => {
              event.preventDefault();
              getToken();
            }}>
              <div className="form-group">
                <label htmlFor="name">What is your username?</label>
                <input type="text" id="name" onChange={e => setUsername(e.target.value)} className="form-control" placeholder="Your username here..."/>
              </div>
              <button id="send" className="btn btn-default" type="submit">Get Token</button>
            </form>
          </div>
        </div>
        <div className="row">
          <div className="col-md-12">
            <table id="conversation" className="table table-striped">
              <thead>
              <tr>
                <th>Solicitations</th>
              </tr>
              </thead>
              <tbody id="solicitations">
              {
                tableData.map((message, index) => {
                  return (
                      <tr key={index}>
                        <td className='white-space:nowrap;word-wrap:break-word;'>{message}</td>
                      </tr>
                  )
                })
              }
              </tbody>
            </table>
          </div>
        </div>
      </div>
  );
}

export default App;
