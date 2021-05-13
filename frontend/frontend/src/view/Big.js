import { useState } from 'react';
import Results from '../components/results';
import SearchId from '../components/searchId';
import SoftNCare from '../components/softNcare';
import Service from '../service/service';
import CacheStats from '../components/cachestats';
import {Row, Col} from 'react-bootstrap';

export default function Big(){

    const [city, setCity] = useState(false);

    const handlerName = (name, country) => {
        console.log(name, country);
        Service.getNameNCountry(name, country)
        .then((res) => {
            if(res.ok ){
                return res.json()
            }
            setCity(null);
        })
        .then((res) => {setCity(res);  console.log(res)});
    }


    const handlerId = (id) => {
        Service.getId(id)
        .then((res) => {
            if(res.ok)
                return res.json();
            setCity(null);
        })
        .then( (res) => {setCity(res); console.log(res)});
    }

    return (
        <div>
            <SoftNCare handler={handlerName}/>
            <SearchId handler={handlerId} />
            <Row>
                <Col md="4"></Col>
                <Col>
            {   
                city == false ? "" : city != null
                ? <Results style={{margin:"auto"}} city={city} />
                : <h1>City Data not found</h1>
            }
                </Col>
            </Row>
            <hr/>
            <CacheStats/>
        </div>
    )




}