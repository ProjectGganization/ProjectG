import { useState, useEffect } from 'react';
import logo from './logo.svg';
import './App.css';

interface ApiResponse {
  message: string;
  status: string;
}

interface StatusResponse {
  status: string;
  timestamp: number;
}

function App() {
  const [message, setMessage] = useState<string>('Loading...');
  const [apiStatus, setApiStatus] = useState<StatusResponse | null>(null);
  const [error, setError] = useState<string>('');

  const API_BASE_URL = 'http://localhost:8080/api';

  useEffect(() => {
    fetchHelloMessage();
    fetchStatus();
  }, []);

  const fetchHelloMessage = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/hello`);
      const data: ApiResponse = await response.json();
      setMessage(data.message);
      setError('');
    } catch (err) {
      setError('Failed to connect to backend. Make sure Spring Boot is running on port 8080.');
      setMessage('');
    }
  };

  const fetchStatus = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/status`);
      const data: StatusResponse = await response.json();
      setApiStatus(data);
    } catch (err) {
      console.error('Failed to fetch status', err);
    }
  };

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>React + Spring Boot Project</h1>

        {error && (
          <div style={{ color: '#ff6b6b', marginBottom: '20px' }}>
            <p>{error}</p>
          </div>
        )}

        {message && (
          <div style={{ marginBottom: '20px' }}>
            <h2>Message from Backend:</h2>
            <p style={{ fontSize: '1.2em', color: '#61dafb' }}>{message}</p>
          </div>
        )}

        {apiStatus && (
          <div style={{ marginBottom: '20px' }}>
            <h3>Backend Status:</h3>
            <p>
              Status: <span style={{ color: apiStatus.status === 'online' ? '#4caf50' : '#ff6b6b', fontWeight: 'bold' }}>{apiStatus.status}</span>
            </p>
            <p>Timestamp: {new Date(apiStatus.timestamp).toLocaleString()}</p>
          </div>
        )}

        <button
          onClick={() => { fetchHelloMessage(); fetchStatus(); }}
          style={{
            padding: '10px 20px',
            fontSize: '16px',
            cursor: 'pointer',
            backgroundColor: '#61dafb',
            border: 'none',
            borderRadius: '5px',
            color: '#282c34'
          }}
        >
          Refresh Data
        </button>
      </header>
    </div>
  );
}

export default App;
