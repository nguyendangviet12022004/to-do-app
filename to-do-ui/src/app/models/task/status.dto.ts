import { StatusValue } from "../../constants/StatusValue";

export interface StatusDTO{
    id: number;
    value: StatusValue;
    timestamp: string;
}