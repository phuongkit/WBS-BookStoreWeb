import React from 'react';
import { HashRouter as Router, BrowserRouter } from 'react-router-dom';
import Routes from './routes';
import ScrollTop from './components/ScrollTop';
import { createHashHistory } from 'history';

const history = createHashHistory({ queryKey: false });

function App() {
    return (
        <Router history={history}>
            <div className="App">
                <ScrollTop>
                    <Routes />
                </ScrollTop>
            </div>
        </Router>
    );
}

export default App;
