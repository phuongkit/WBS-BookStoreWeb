import React, { useState } from 'react';
import './AdminLayout.scss';
import { Outlet, useLocation } from 'react-router-dom';
import Sidebar from '../../Sidebar/Sidebar';
import Navbar from '../../Navbar/Navbar';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import { amber, grey, purple, lightBlue } from '@mui/material/colors';

// import { ThemeProvider as Emotion10ThemeProvider } from 'emotion-theming';

function AdminLayout({ children }) {
    const location = useLocation();
    const isShowLayout = location.pathname !== '/login';

    const [mode, setMode] = useState('light');

    const darkTheme = createTheme({
        palette: {
            mode,
            ...(mode === 'light'
      ? {
          // palette values for light mode
        //   primary: amber,
        //   divider: amber[200],
          text: {
            primary: grey[900],
            secondary: grey[800],
          },
        }
      : {
          // palette values for dark mode
        //   primary: lightBlue,
        //   divider: lightBlue[700],
        //   background: {
        //     default: lightBlue[900],
        //     paper: lightBlue[900],
        //   },
          text: {
            primary: '#fff',
            secondary: grey[500],
          },
        }),
        },
    });
    // const darkTheme = useTheme();

    return (
        <>
            {isShowLayout ? (
                <ThemeProvider theme={darkTheme}>
                    <CssBaseline />
                    <div className="list">
                        <Sidebar onChangeMode={setMode} />
                        <div className="listContainer">
                            <Navbar mode={mode}/>
                            <Outlet></Outlet>
                        </div>
                    </div>
                </ThemeProvider>
            ) : (
                <>
                    <Outlet></Outlet>
                </>
            )}
        </>
    );
}

export default AdminLayout;