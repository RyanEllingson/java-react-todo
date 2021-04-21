import React from "react";

const Form = function({title, fields, inputs, inputChangeHandler, errors, submitHandler, isLoading, children}) {
    return (
        <div className="Form">
            <div className="card shadow mb-5">
                <div className="card-body">
                    <h5 className="card-title text-center mb-3">{title}</h5>
                    <form className="mb-3" onSubmit={submitHandler}>
                        {fields.map((field, index) => {
                            const {fieldName, fieldText, fieldType} = field;
                            return (<div className="form-floating mb-3" key={index}>
                                <input type={fieldType} className={errors[fieldName] ? "form-control is-invalid" : "form-control"} id={`${fieldName}Input`} aria-describedby={fieldName} placeholder={fieldName} name={fieldName} value={inputs[fieldName]} onChange={inputChangeHandler} />
                                <label htmlFor={`${fieldName}Input`} className="form-label">{fieldText}</label>
                                {errors[fieldName] && <div id={`${fieldName}Error`} className="form-text text-danger">{errors[fieldName]}</div>}
                            </div>);
                        })}
                        {isLoading
                        ? <button className="btn btn-primary" type="button" disabled>
                            <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                            Loading...
                        </button>
                        : <button type="submit" className="btn btn-primary">Submit</button>}
                    </form>
                    {children}
                </div>
            </div>
        </div>
    );
};

export default Form;