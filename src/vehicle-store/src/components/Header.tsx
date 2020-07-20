import React from 'react';

import "../tailwind.css";


const Header = () => {
  return (
    <div className="flex justify-between p-5 px-10">
      <h1>Header</h1>

      <button>Login</button>
    </div>
  );
};

export default Header;
