import React from 'react';
import { HashRouter } from 'react-router-dom';
import Routes from './routes';
import ScrollTop from './components/ScrollTop';

function App() {
    return (
        <HashRouter>
            <div className="App">
                <ScrollTop>
                    <Routes />
                </ScrollTop>
            </div>
        </HashRouter>
    );
}

export default App;
