import SoftNCare from '../component/softNcare';
import PencilCase from '../service/pencilcase';

export default function Big(){


    const handlerName = (name, country) => {
        console.log(name, country);
        PencilCase.getNameNCountry(name, country)
        .then((res) => console.log(res.json()));
    }


    return (
        <div>
            <SoftNCare handler={handlerName}/>   
        </div>
    )




}