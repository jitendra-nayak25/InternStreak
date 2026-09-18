
import { useParams, useNavigate } from "react-router-dom";

function Payment() {

    const { id } = useParams();

    const navigate = useNavigate();

    return (
        <div>

            <h1>
                Course Payment
            </h1>

            <p>
                Course ID: <strong>{id}</strong>
            </p>

            <p>
                Payment Status:{" "}
                <strong>Pending</strong>
            </p>

            <p>
                Please complete the payment for this course.
            </p>

            <button
                onClick={() =>
                    alert("Payment processing will be implemented here.")
                }
            >
                Proceed to Payment
            </button>

            {" "}

            <button
                onClick={() =>
                    navigate("/")
                }
            >
                Back to Dashboard
            </button>

        </div>
    );
}

export default Payment;

