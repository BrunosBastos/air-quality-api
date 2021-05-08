
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

                </Row>
            </Card.Body>
        </Card>
           
    )
}