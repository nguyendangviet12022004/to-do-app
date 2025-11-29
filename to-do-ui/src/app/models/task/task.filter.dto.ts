import { StatusValue } from "../../constants/StatusValue";

export interface TaskFilterDTO {
    categoryIds?: number[];
    tagIds?: number[];
    priorities?: number[];
    status?: StatusValue[];
}