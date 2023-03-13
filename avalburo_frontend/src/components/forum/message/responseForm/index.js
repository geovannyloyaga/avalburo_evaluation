import axios from "axios";
import { useState } from "react";
import { FloatingLabel, Form } from "react-bootstrap";
import { toast } from "react-toastify";
import SendImg from '../../../../assets/img/send.png';

const ResponseForm =({message, messageParentId, userId, getMessages}) => {
    const [data, setData] = useState({ comment: '', userId: userId, responseId: null });

    function save(event) {
        event.preventDefault();
        let parametersApi = data;
        if (message?.parent) {
            parametersApi.responseId = message.id;
        }
        axios.post(`http://localhost:8080/api/messages/${messageParentId}/saveResponse`, parametersApi)
            .then(function (response) {
                let responseApi = response.data;
                if (responseApi.code === 200) {
                    getMessages();
                    return;
                }
                toast(response?.error ?? 'Hubo un inconveniente al consultar al servidor!');
            })
            .catch(function (error) {
                // handle error
                toast(error?.data ?? 'Hubo un inconveniente al consultar al servidor!');
            })
            .finally(function () {
                // always executed
            });
    }

    const handleInputChange = (event) => {
        setData({
            ...data,
            [event.target.name] : event.target.value
        })
    }

    return <FloatingLabel className="w-100" controlId="floatingTextarea2" label="Escribe la pregunta">
    <Form.Control
        as="textarea"
        name="comment"
        placeholder="Escribe la pregunta"
        onChange={handleInputChange.bind(this)}
        className="w-100"
    />
    <button className="floatingButton" onClick={save.bind(this)}><img src={SendImg} alt="enviar" height={40} width={40} /></button>
</FloatingLabel>;
};

export default ResponseForm;