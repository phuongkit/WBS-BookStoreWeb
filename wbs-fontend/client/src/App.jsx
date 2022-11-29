import React from 'react';
import { HashRouter, BrowserRouter } from 'react-router-dom';
import Routes from './routes';
import ScrollTop from './components/ScrollTop';

function App() {
    return (
        <BrowserRouter basename="/">
            <div className="App">
                <ScrollTop>
                    <Routes />
                </ScrollTop>
            </div>
        </BrowserRouter>
    );
}

export default App;
