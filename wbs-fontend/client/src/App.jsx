import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import Routes from './routes';
import ScrollTop from './components/ScrollTop';
import './App.css';
function App() {
    return (
        <Router>
            <div className="App">
                <ScrollTop>
                    <Routes />
                </ScrollTop>
            </div>
        </Router>
    );
}

export default App;
