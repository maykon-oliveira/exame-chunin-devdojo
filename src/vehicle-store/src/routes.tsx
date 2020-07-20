import { Route, BrowserRouter } from 'react-router-dom';

import React from 'react';
import Home from './pages/Home';
import Header from './components/Header';

export const Routes = () => {
    return (
        <BrowserRouter>
            <Header/>
            <Route path="/" component={Home} exact />
        </BrowserRouter>
    );
};
