import {TextField, Button} from '@material-ui/core';
import {Row} from 'react-bootstrap';

export default function SoftNCare(props){

    return(
        <div className="container">
            <Row className="my-4">
                <TextField id="name" name="name" label="City Name" variant="outlined" />
                <TextField className="mx-4" id="country" name="country" label="Country Code" variant="outlined" />
            </Row>
            <Row>
                <Button id="search_by_name"
                onClick={ () => props.handler(
                    document.getElementById("name").value,
                    document.getElementById("country").value
                    )} 
                color="primary" variant="contained"
                >
                    Search 
                </Button>
            </Row>
        </div>
    )


}