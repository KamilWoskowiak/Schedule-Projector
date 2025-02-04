export interface Course {
    id: string;
    name: string;
    credits: number;
    prerequisites: string[];
    offeredIn: string[];
}