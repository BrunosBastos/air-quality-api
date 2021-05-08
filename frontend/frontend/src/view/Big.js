import { useState } from 'react';
import Results from '../components/results';
import SearchId from '../components/searchId';
import SoftNCare from '../components/softNcare';
import PencilCase from '../service/pencilcase';

export default function Big(){

    const [city, setCity] = useState(null);

    const handlerName = (name, country) => {
        console.log(name, country);
        PencilCase.getNameNCountry(name, country)
        .then((res) => {
            if(res.ok ){
                return res.json()
            }
            setCity(null);
        })
        .then((res) => {setCity(res)});
    }


    const handlerId = (id) => {
        PencilCase.getId(id)
        .then((res) => {
            if(res.ok)
                return res.json();
            setCity(null);
        })
        .then( (res) => {setCity(res)});
    }

    return (
        <div>
            <SoftNCare handler={handlerName}/>
            <SearchId handler={handlerId} />
            {   
                city
                ? <Results city={city} />
                : <h1>City Data not found</h1>
            }


        </div>
    )




}