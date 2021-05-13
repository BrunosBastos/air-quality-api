import { useState } from 'react';
import {Row, Col, Button} from 'react-bootstrap';
import Service from '../service/service';

export default function CacheStats(props){

    const [misses, setMisses] = useState(0);
    const [hits, setHits] = useState(0);
    const [requests, setRequests] = useState(0);


    const submit = () => {

        Service.getCache()
        .then((res) => {return res.json()})
        .then(
            (res) => {
                setHits(res.hits);
                setMisses(res.misses);
                setRequests(res.requests);
            }
        )

    }

    return (
        <div style={{textAlign: 'center'}}>
            <Row>
                <Col>
                    <h3>Requests</h3>
                </Col>
                <Col>
                    <h3>Hits</h3>
                </Col>
                <Col>
                    <h3>Misses</h3>
                </Col>
            </Row>
            <Row>
                <Col>
                    <h1 id="requests">{requests}</h1>
                </Col>
                <Col>
                    <h1 id="hits">{hits}</h1>
                </Col>
                <Col>
                    <h1 id="misses">{misses}</h1>
                </Col>
            </Row>
            <Row>
                <Col>
                    <Button id="refresh" onClick={submit}>Refresh</Button>
                </Col>
            </Row>
        </div>
    )


}
