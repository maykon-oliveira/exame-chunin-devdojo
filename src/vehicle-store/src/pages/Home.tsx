import React, { useState, useEffect } from 'react';
import { Api } from '../services/api';

interface Vehicle {
  id: number;
  brand: string;
  model: string;
  year: number;
  price: number;
  thumbnail: string;
}

const Home = () => {
  const [vehicles, setVehicles] = useState<Vehicle[]>([]);
  const currencryFormatter = new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' });

  useEffect(() => {
    Api.get<Vehicle[]>('/vehicles').then(({ data }) => {
      setVehicles(data);
    });
  }, []);

  return (
    <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
      <div className="flex flex-wrap -mb-4">
        {vehicles.map((vehicle) => (
          <div key={vehicle.id} className="w-1/3 py-4 mb-4">
            <div className="px-4 sm:px-0">
              <div className="max-w-sm rounded overflow-hidden shadow-lg">
                <img
                  style={{ height: '250px' }}
                  className="w-full"
                  src={`http://localhost:8080${vehicle.thumbnail}`}
                />
                <div className="px-6 py-4">
                  <div className="font-bold text-xl mb-2">
                    {vehicle.model} - {vehicle.brand}
                  </div>
                  <p className="text-gray-700 text-base flex justify-between">
                    <span>{vehicle.year}</span>
                    <span>{currencryFormatter.format(vehicle.price)}</span>
                  </p>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Home;
