import React, { useEffect, useState } from "react";
import { AiOutlineClose } from "react-icons/ai";
import { GiBroom } from "react-icons/gi";
import { DeleteMethod, GetMethod } from "../utils/RestMethods";
import { toast } from 'react-toastify';

export interface Filter {
    filter: string;
    id?: number;
    date?: Date;
};

export const FilterInput = (props: any): JSX.Element => {
    const [selected, setSelected] = useState(false);
    const [filter, setFilter] = useState<Filter[]>([]);

    const updateHistory = (): void => {
        GetMethod("http://localhost:8080/api/v1/filter", (status: number, response: any) => {
            if (status === 200) setFilter(response);
        });
    };

    useEffect(() => { updateHistory(); });

    const deleteHistoryOption = (param: Filter): void => {
        const params = JSON.stringify(param);

        DeleteMethod("http://localhost:8080/api/v1/filter", params, (status: number, response: any) => {
            if (status === 202) {
                toast("Item do histórico deletado com sucesso!");
            }

            if (status >= 400) {
                const error = "Error: " + response.error,
                    msg = "Message: " + response.message;
                console.error(error);
                console.error(msg);

                toast("Ocorreu um erro ao deletar o item do histórico.");
            }
        });
    };

    const renderHistory = (): JSX.Element => {
        if (filter.length === 0) return (<div></div>);

        return (
            <div>
                {
                    filter.map((value: Filter, index: number) => (
                        <div key={index}>
                            <p defaultValue={value.filter} onClick={() => props.updateDefaultValue(value, "filter")}>
                                {value.filter}
                            </p>
                            <button onClick={() => deleteHistoryOption(value)}>
                                <AiOutlineClose />
                            </button>
                        </div>
                    ))
                }
            </div>
        );
    };

    document.addEventListener('click', (e) => {
        const nav = document.getElementById(props.id);

        const elementsExist = selected && nav;
        if (elementsExist && !nav.contains(e.target as Node)) {
            setSelected(false);
        }
    });

    return (
        <>
            <h2>{props.title}</h2>
            <div className="input-content">
                <input
                    type="text"
                    name={props.name}
                    id={props.id}
                    value={props.defaultValue}
                    placeholder={props.placeholder}
                    onChange={(e) => {
                        const filter = {
                            filter: e.target.value,
                            date: new Date()
                        };
                        props.updateDefaultValue(filter, "filter");
                    }}
                    onClick={() => setSelected(true)} />

                <button className="input-buttons" onClick={() => props.updateDefaultValue({ filter: "" }, "filter")}>
                    <GiBroom />
                </button>
            </div>

            {selected && renderHistory()}
        </>
    );
};
