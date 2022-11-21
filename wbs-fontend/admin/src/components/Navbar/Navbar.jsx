import React from 'react';
import './navbar.scss';
import {
    SearchOutlined,
    LanguageOutlined,
    LightModeOutlined,
    DarkModeOutlined,
    FullscreenExitOutlined,
    NotificationsNoneOutlined,
    ChatBubbleOutlineOutlined,
    ListOutlined,
} from '@mui/icons-material';
import { useTheme } from '@emotion/react';
import { pseudo } from '../../utils/utils';

const Navbar = ({ mode }) => {
    const theme = useTheme();
    const placeholder = pseudo('::-webkit-input-placeholder', { color: theme.palette.text.primary });
    return (
        <div className="admin-navbar">
            <div className="wrapper">
                <div className="search">
                    {placeholder.injectStyle()}
                    <input
                        className={placeholder.className}
                        type="text"
                        placeholder="Search..."
                        style={{ color: theme.palette.text.primary }}
                        rul
                    />
                    <SearchOutlined sx={{ color: 'text.primary' }} />
                </div>
                <div className="items">
                    <div className="item" style={{ color: theme.palette.text.primary }}>
                        <LanguageOutlined className="icon" sx={{ color: 'text.primary' }} />
                        English
                    </div>
                    {mode === 'dark' ? (
                        <div className="item">
                            <DarkModeOutlined className="icon" sx={{ color: 'text.primary' }} />
                        </div>
                    ) : (
                        <div className="item">
                            <LightModeOutlined className="icon" sx={{ color: 'text.primary' }} />
                        </div>
                    )}
                    <div className="item">
                        <FullscreenExitOutlined className="icon" sx={{ color: 'text.primary' }} />
                    </div>
                    <div className="item">
                        <NotificationsNoneOutlined className="icon" sx={{ color: 'text.primary' }} />
                        <div className="counter">1</div>
                    </div>
                    <div className="item">
                        <ChatBubbleOutlineOutlined className="icon" sx={{ color: 'text.primary' }} />
                        <div className="counter">2</div>
                    </div>
                    <div className="item">
                        <ListOutlined className="icon" sx={{ color: 'text.primary' }} />
                    </div>
                    <div className="item">
                        <img
                            src="https://images.pexels.com/photos/941693/pexels-photo-941693.jpeg?auto=compress&cs=tinysrgb&dpr=2&w=500"
                            alt=""
                            className="avatar"
                        />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Navbar;
