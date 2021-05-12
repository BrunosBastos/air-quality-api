
import {Row, Col, Card} from 'react-bootstrap';

export default function Results(props){


    return (
        <Card
            bg={'light'}
            style={{ width: '18rem' }}
            className="mb-2"
        >
            <Card.Body>
                <Row>
                    <Col>
                        {props.city.city_name}
                    </Col>

                    <Col>
                        {props.city.country_code}
                    </Col>
                    <Col>
                        {props.city.lat}, {props.city.lon}
                    </Col>
                </Row>
                <hr/>
                <Row>
                    <Col>
                    {Object.keys(props.city.data[0]).map((k, i) => {
                        {console.log("wtf")}
                        return(
                            <Row>
                                <Col>
                                    <h6>{k} : {props.city.data[0][k]}</h6>
                                </Col>
                            </Row>
                        )
                        }) 
                    }
                    </Col>
                </Row>
            </Card.Body>
        </Card>
           
    )
}