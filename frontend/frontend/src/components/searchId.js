import {Row} from 'react-bootstrap';
import {TextField, Button} from '@material-ui/core';

export default function SearchId(props){

    return (

        <div className="container">
            <Row className="my-4">
                <TextField id="city_id" name="city_id" label="City ID" variant="outlined" />
            </Row>
            <Row>
                <Button 
                onClick={ () => props.handler(
                    document.getElementById("city_id").value
                    )} 
                color="primary" variant="contained"
                >
                    Search 
                </Button>
            </Row>
        </div>


    )


}